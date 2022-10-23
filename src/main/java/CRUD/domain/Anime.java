package CRUD.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Anime{
    Integer id;
    String name;
    int episodes;
    Integer producer_id;
}