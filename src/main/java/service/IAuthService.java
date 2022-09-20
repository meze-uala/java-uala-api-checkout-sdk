package service;

import dto.AuthRequest;

import java.io.IOException;

public interface IAuthService {
    String getAuthToken(AuthRequest request) throws IOException;
}
