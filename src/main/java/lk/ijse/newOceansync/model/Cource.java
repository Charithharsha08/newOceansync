package lk.ijse.newOceansync.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Cource {
    public String courceId;
    public String Name;
    private String Duration;
    private double Cost;
}
