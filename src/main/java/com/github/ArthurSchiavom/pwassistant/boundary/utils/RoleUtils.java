package com.github.ArthurSchiavom.pwassistant.boundary.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleUtils {
    public static void toggleRole(final Guild guild,
                                                       final long roleId,
                                                       final Member member,
                                                       final Consumer<Void> successAddRole,
                                                       final Consumer<Void> successRemoveRole,
                                                       final Consumer<? super Throwable> failure) {
        final Role role = guild.getRoleById(roleId);
        final List<Long> roles = MemberUtils.getMemberRolesIds(member);
        final boolean removeRole = roles.contains(roleId);
        if (removeRole) {
            guild.removeRoleFromMember(member.getUser(), role).queue(successRemoveRole, failure);
        } else {
            guild.addRoleToMember(member.getUser(), role).queue(successAddRole, failure);
        }
    }
}
