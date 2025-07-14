package social.network.backend.socialnetwork.facade.mapper;


public interface Mapper<E,U,C,G> {

    /**
     * Converts a DTO for creation to an entity.
     *
     * @param dtoForCreate the DTO for creation
     * @return the entity
     */
    E toEntityFromCreate(C dtoForCreate);

    /**
     * Converts a DTO for update to an entity.
     *
     * @param dtoForUpdate the DTO for update
     * @return the entity
     */
    E toEntityFromUpdate(U dtoForUpdate);

    /**
     * Converts an entity to a DTO.
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    G toDto(E entity);


}
