package social.network.backend.socialnetwork.facade.mapper;


public interface Mapper<E,U,C,G> {

    E toEntityFromCreate(C dtoForCreate);

    E toEntityFromUpdate(U dtoForUpdate);

    G toDto(E entity);


}
