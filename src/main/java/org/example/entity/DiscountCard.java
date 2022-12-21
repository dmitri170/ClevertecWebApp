package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.DAL.models.CardDTO;
import org.example.DAL.models.ProductDTO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "discount_card",schema = "todolist",catalog = "clevertec")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "number", nullable = false, length = -1)
    private Integer number;
    @Basic
    @Column(name = "discount", nullable = false, precision = 0)
    private Integer discount;
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number=" + number +
                ", discount=" + discount +
                '}';
    }
    public CardDTO toDTO() {

        CardDTO cardDTO=new CardDTO();
        cardDTO.setDiscount(discount);
        cardDTO.setNumber(number);
        return cardDTO;
    }
}

