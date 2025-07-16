package social.network.backend.socialnetwork.facade.mapper.impl.image;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import social.network.backend.socialnetwork.dto.image.GetImageDTO;
import social.network.backend.socialnetwork.entity.Image;
import social.network.backend.socialnetwork.facade.mapper.Mapper;

@Component
public final class ImageMapperImpl implements Mapper<Image, GetImageDTO> {

    @Contract("_ -> new")
    @Override
    public @NotNull GetImageDTO toDto(final @NotNull Image entity) {
        return new GetImageDTO(
                entity.getId(),
                entity.getFilePath()
        );
    }
}
