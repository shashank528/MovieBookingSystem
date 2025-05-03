package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@ToString
public class User {
    final int userId;
    final String userName;
    final String email;
}
