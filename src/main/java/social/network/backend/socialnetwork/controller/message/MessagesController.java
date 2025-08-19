package social.network.backend.socialnetwork.controller.message;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.facade.MessageFacade;

import java.util.Map;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public final class MessagesController {

    private final MessageFacade messageFacade;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.getUserById(#createMessageDTO.senderId()).email == principal.username")
    public ResponseEntity<?> createMessage(final @RequestBody CreateMessageDTO createMessageDTO, final BindingResult result,
                                           final UriComponentsBuilder uriComponentsBuilder) {
        final GetMessageDTO createdMessage = this.messageFacade.createMessage(createMessageDTO, result);

        return created(uriComponentsBuilder
                        .replacePath("/api/v1/messages/{messageId}")
                        .build(Map.of("messageId", createdMessage.id()))
        ).body(createdMessage);
    }

    @GetMapping("/user/{userId:\\d+}")
    @PreAuthorize("hasRole('ADMIN') or @userServiceImpl.getUserById(#userId).email == principal.username")
    public ResponseEntity<?> getByUserId(final @PageableDefault Pageable pageable, final @PathVariable("userId") Integer userId) {
        final Page<GetMessageDTO> messages = this.messageFacade.getAllMessagesByUserId(userId,pageable);

        return ok(messages);
    }
}
