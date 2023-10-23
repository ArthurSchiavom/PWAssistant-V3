package com.github.ArthurSchiavom.pwassistant.boundary.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUtils {
    public static List<Long> getMemberRolesIds(Member member) {
        List<Long> rolesIds = new ArrayList<>();
        for (Role role : member.getRoles()) {
            rolesIds.add(role.getIdLong());
        }
        return rolesIds;
    }
}
