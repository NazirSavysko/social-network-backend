package social.network.backend.socialnetwork.validation;

import org.springframework.validation.BindingResult;

public interface DtoValidator {
    void validate(Object dto, BindingResult result);
}