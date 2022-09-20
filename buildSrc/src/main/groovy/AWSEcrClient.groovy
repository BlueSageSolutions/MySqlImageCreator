import groovy.transform.CompileStatic
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ecr.EcrClient
import software.amazon.awssdk.services.ecr.model.GetAuthorizationTokenRequest
import software.amazon.awssdk.services.ecr.model.GetAuthorizationTokenResponse
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient

@CompileStatic
class AWSEcrClient {
    SecretsManagerClient secretsClient
    EcrClient ecrClient

    AWSEcrClient(String id, String key) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(id,key)
        ecrClient = EcrClient.builder().region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(credentials)).build()
    }

    String getToken(String id) {
        GetAuthorizationTokenRequest request= GetAuthorizationTokenRequest.builder().registryIds(id).build() as GetAuthorizationTokenRequest
        GetAuthorizationTokenResponse response = ecrClient.getAuthorizationToken(request)
        String encodedToken = response.authorizationData().first().authorizationToken()
        return new String(Base64.getDecoder().decode(encodedToken.bytes))

    }
}