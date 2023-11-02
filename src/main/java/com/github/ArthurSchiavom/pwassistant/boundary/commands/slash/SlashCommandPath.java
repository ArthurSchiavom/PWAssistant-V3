package com.github.ArthurSchiavom.pwassistant.boundary.commands.slash;

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

    public String getFullPath() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (subGroupName != null) {
            sb.append(" ").append(subGroupName);
        }
        if (subCommandName != null) {
            sb.append(" ").append(subCommandName);
        }
        return sb.toString();
    }
}
