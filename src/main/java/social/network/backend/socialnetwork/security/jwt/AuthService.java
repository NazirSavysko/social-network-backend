package social.network.backend.socialnetwork.security.jwt;

public interface AuthService {
    String authenticateAndIssueJwt(String email, String rawPassword);
}