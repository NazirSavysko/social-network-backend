package social.network.backend.socialnetwork.controller.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import social.network.backend.socialnetwork.dto.message.CreateMessageDTO;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.facade.MessageFacade;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/messages")
public final class MessagesController {

    private final MessageFacade messageFacade;

    @Autowired
    public MessagesController(final MessageFacade messageFacade) {
        this.messageFacade = messageFacade;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMessage(final @RequestBody CreateMessageDTO createMessageDTO,
                                           final BindingResult result,
                                           final UriComponentsBuilder uriComponentsBuilder) {
        final GetMessageDTO createdMessage = this.messageFacade.createMessage(createMessageDTO, result);

        return created(uriComponentsBuilder
                        .replacePath("/api/v1/messages/{messageId}")
                        .build(Map.of("messageId", createdMessage.id()))
                )
                .body(createdMessage);
    }

    @GetMapping("/user/{userId:\\d+}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") Integer userId) {
        final List<GetMessageDTO> messages = this.messageFacade.getAllMessagesByUserId(userId);

        return ok()
                .body(messages);
    }
}
