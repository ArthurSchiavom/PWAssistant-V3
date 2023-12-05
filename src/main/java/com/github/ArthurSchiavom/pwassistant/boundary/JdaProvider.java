package com.github.ArthurSchiavom.pwassistant.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.JDA;

@ApplicationScoped
public class JdaProvider {

    @Getter
    @Setter(AccessLevel.PACKAGE)
    JDA jda;
}
