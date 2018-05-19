package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "items")
public class Item {

    public enum Type {
        BORROWED, RESERVED, AVAILABLE, UNAVAILABLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "titleId")
    private Long titleId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private Type status;
}
