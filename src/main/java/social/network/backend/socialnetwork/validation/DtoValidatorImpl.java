package social.network.backend.socialnetwork.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.stream.Collectors;

@Component
public final class DtoValidatorImpl implements DtoValidator {

    private final Validator validator;

    @Autowired
    public DtoValidatorImpl(final Validator validator) {
        this.validator = validator;
    }

    public void validate(final Object dto,final BindingResult result) {
        validator.validate(dto, result);
        if (result.hasErrors()) {
            final String message = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));

            throw new IllegalArgumentException(message);
        }
    }
}
