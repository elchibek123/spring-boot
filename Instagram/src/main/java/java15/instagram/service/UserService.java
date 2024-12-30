package java15.instagram.service;

import java15.instagram.model.dto.request.UserRequest;
import java15.instagram.model.dto.response.SimpleResponseMessage;

public interface UserService {
    SimpleResponseMessage register(UserRequest request);
}
