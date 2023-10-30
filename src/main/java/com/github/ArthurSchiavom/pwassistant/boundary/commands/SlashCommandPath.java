package com.github.ArthurSchiavom.pwassistant.boundary.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class SlashCommandPath {
    private String name;
    private String subGroupName;
    private String subCommandName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlashCommandPath that = (SlashCommandPath) o;
        return Objects.equals(name, that.name) && Objects.equals(subGroupName, that.subGroupName) && Objects.equals(subCommandName, that.subCommandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subGroupName, subCommandName);
    }
}
