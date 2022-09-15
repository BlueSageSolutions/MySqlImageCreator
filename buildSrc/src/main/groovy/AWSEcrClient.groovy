import groovy.transform.CompileStatic
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ecr.EcrClient
import software.amazon.awssdk.services.ecr.model.GetAuthorizationTokenRequest
import software.amazon.awssdk.services.ecr.model.GetAuthorizationTokenResponse
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient

@CompileStatic
class AWSEcrClient {
    SecretsManagerClient secretsClient
    EcrClient ecrClient

    AWSEcrClient() {
        ecrClient = EcrClient.builder().region(Region.AP_NORTHEAST_1).credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build()
    }

    String getToken() {
        GetAuthorizationTokenRequest request= GetAuthorizationTokenRequest.builder().registryIds().build() as GetAuthorizationTokenRequest
        GetAuthorizationTokenResponse response = ecrClient.getAuthorizationToken(request)
        println response.authorizationData()

    }
}