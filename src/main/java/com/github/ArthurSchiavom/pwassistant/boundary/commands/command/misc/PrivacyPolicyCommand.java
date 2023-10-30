package com.github.ArthurSchiavom.pwassistant.boundary.commands.command.misc;

import jakarta.enterprise.context.ApplicationScoped;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@ApplicationScoped
public class PrivacyPolicyCommand implements SlashCommand {
    private static final String NAME = "privacy";
    private static final String DESCRIPTION = "Privacy Policy";

    private static final String[] MESSAGE = {"""
            # Overview
            We follow all legal requirements to protect your privacy. Our Privacy Policy is a legal statement that explains how we may collect information from you, how we may share your information, and how you can limit our sharing of your information. You will see terms in our Privacy Policy that are capitalized. These terms have meanings as described in the Definitions section below.
            # Section 1 - Definitions
            "Non Personal Information" is information that is not personally identifiable to you and that we automatically collect when you access our Service. It may also include publicly available information that is shared between you and others. "Personally Identifiable Information" is non-public information that is personally identifiable to you and obtained in order for us to provide you within our Service. Personally Identifiable Information may include information such as your discord username, discord discriminator, discord avatar, and other related information that you provide to us or that we obtain about you.
            # Section 2 - Information We Collect
            Generally, you control the amount and type of information you provide to us when using our Service. As a visitor, you can browse our Service to find out more about our Service. You are not required to provide us with any Personally Identifiable Information as a visitor. As a user, you may be required to provide us with Personally Identifiable Information.""",
            """
            # Section 3 - How We Use Your Information
            We use the information we receive from you as follows:
            * to customize our service to fit your needs
            * to provide customer support
            * to contact you
            # Section 4 - Sharing Information With Affiliates and Other Third Parties
            We do not sell, rent, or otherwise provide your Personally Identifiable Information to third parties.
            # Section 5 - Data Aggregation
            We retain the right to collect and use any Non Personal Information collected from your use of our Service and aggregate such data for internal analytics that improve our Service as well as for use or resale to others. At no time is your Personally Identifiable Information included in such data aggregations.
            # Section 6 - Legally Required Releases of Information
            We may be legally required to disclose your Personally Identifiable Information, if such disclosure is (a) required by subpoena, law, or other legal process; (b) necessary to assist law enforcement officials or government enforcement agencies; (c) necessary to investigate violations of or otherwise enforce our Legal Terms; (d) necessary to protect us from legal action or claims from third parties including you and/or other Members; and/or (e) necessary to protect the legal rights, personal/real property, or personal safety of PWAssistant developers, our users, employees, and affiliates.""",
            """
            # Section 7 - Links to Other Services
            Our Service may contain links to other websites that are not under our direct control. These websites may have their own policies regarding privacy. We have no control of or responsibility for linked websites and provide these links solely for the convenience and information of our visitors. You access such linked Services at your own risk. These websites are not subject to this Privacy Policy. You should check the privacy policies, if any, of those individual websites to see how the operators of those third-party websites will utilize your personal information. In addition, these websites may contain a link to Services of our affiliates. The websites of our affiliates are not subject to this Privacy Policy, and you should check their individual privacy policies to see how the operators of such websites will utilize your personal information.
            # Section 8 - Privacy Policy Updates
            We reserve the right to modify this Privacy Policy at any time. You should review this Privacy Policy frequently. If we make material changes to this policy, we may notify you on our Service, by a blog post, by email, or by any method we determine. The method we choose is at our sole discretion.
            # Section 9 - Contact Information
            Questions about the Privacy Policy should be sent to us via email at pwassistantbot@gmail.com"""};

    private final CommandData commandData = Commands.slash(NAME, DESCRIPTION);

    @Override
    public CommandData getCommandData() {
        return commandData;
    }

    @Override
    public void execute(final SlashCommandInteractionEvent event) {
        event.reply(MESSAGE[0]).setEphemeral(true)
                .queue(h -> h.sendMessage(MESSAGE[1]).setEphemeral(true)
                        .queue(msg -> h.sendMessage(MESSAGE[2]).setEphemeral(true).queue())
                );
    }
}
