package social.network.backend.socialnetwork.dto.admin;

import java.time.Instant;

public record TimeRangeDTO(
        Instant start,
        Instant end
) {
}
