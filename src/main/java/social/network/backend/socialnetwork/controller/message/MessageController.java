package social.network.backend.socialnetwork.controller.message;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.network.backend.socialnetwork.controller.payload.UpdateMessagePayload;
import social.network.backend.socialnetwork.dto.message.GetMessageDTO;
import social.network.backend.socialnetwork.dto.message.UpdateMessageDTO;
import social.network.backend.socialnetwork.facade.MessageFacade;
import social.network.backend.socialnetwork.facade.impl.MessageFacadeImpl;


@RestController
@RequestMapping("/api/v1/messages/{messageId:\\d+}")
public final class MessageController {

    private final MessageFacade messageFacade;

    @Autowired
    public MessageController(final MessageFacadeImpl messageFacade) {
        this.messageFacade = messageFacade;
    }

    @ModelAttribute("message")
    public @NotNull GetMessageDTO getMessageId(@PathVariable("messageId") Integer messageId) {
      return  this.messageFacade.getMessageById(messageId);
    }

    @GetMapping
    public @NotNull ResponseEntity<?> getMessage(@ModelAttribute("message") GetMessageDTO message) {

        return ResponseEntity
                .ok()
                .body(message);
    }

    @PutMapping("/update")
    public @NotNull ResponseEntity<?> updateMessage(@PathVariable("messageId") Integer messageId,
                                                     final @RequestBody UpdateMessagePayload content,
                                                    final BindingResult result) {

        final UpdateMessageDTO updateMessageDTO = new UpdateMessageDTO(messageId, content.content());
        final GetMessageDTO updatedMessage = this.messageFacade.updateMessage(updateMessageDTO, result);

        return ResponseEntity
                .ok()
                .body(updatedMessage);
    }

    @DeleteMapping("/delete")
    public @NotNull ResponseEntity<?> deleteMessage(@PathVariable("messageId") Integer messageId) {

        this.messageFacade.deleteMessage(messageId);

        return ResponseEntity
                .ok()
                .build();
    }

}
