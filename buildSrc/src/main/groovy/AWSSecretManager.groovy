import groovy.transform.CompileStatic
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse

@CompileStatic
class AWSSecretManger {
    SecretsManagerClient client

    AWSSecretManger() {
        client = SecretsManagerClient.builder().region(Region.AP_NORTHEAST_1).credentialsProvider(EnvironmentVariableCredentialsProvider.create()).build()
    }

    String getSecret(String id) {
        GetSecretValueRequest request = GetSecretValueRequest.builder().secretId(id).build() as GetSecretValueRequest
        GetSecretValueResponse response = client.getSecretValue(request)
        return response.secretString()
    }
}