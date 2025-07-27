package social.network.backend.socialnetwork.mapper;


@FunctionalInterface
public interface Mapper<E,G> {
    G toDto(E entity);
}
