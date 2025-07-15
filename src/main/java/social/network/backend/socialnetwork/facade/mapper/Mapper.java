package social.network.backend.socialnetwork.facade.mapper;


@FunctionalInterface
public interface Mapper<E,G> {
    G toDto(E entity);
}
