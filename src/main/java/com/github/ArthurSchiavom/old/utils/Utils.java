package com.github.ArthurSchiavom.old.utils;

import com.github.ArthurSchiavom.old.information.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.requests.ErrorResponse;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Class that contains utilities that might be useful in multiple places of the app.
 */
public class Utils {
	/*
	In this message, $$game will be converted to the game name,
	$$role to a mention of the game role (@role for servers or the role name for PMs),
	$$usertag to a mention of the user (@user) and
	$$username to the name of the user.
	 */

    /**
     * Formats a message by transforming:
     * <br>$$UserTag to a mention of the user (@user)
     * <br>$$UserTagText to the name + tag of the user (name#tag)
     * <br>$$UserName to the name of the user
     *
     * @param msg         The msg to format.
     * @param user        The user target of the message.
     * @param useRoleName If the role name should be used isntead of a mention.
     * @return The formatted message.
     */
    public static String formatSettingsMessage(String msg, User user, boolean useRoleName) {
        msg = msg.replace("$$UserTagText", user.getAsTag());
        msg = msg.replace("$$UsertagText", user.getAsTag());
        msg = msg.replace("$$UserTagtext", user.getAsTag());
        msg = msg.replace("$$Usertagtext", user.getAsTag());
        msg = msg.replace("$$userTagText", user.getAsTag());
        msg = msg.replace("$$usertagText", user.getAsTag());
        msg = msg.replace("$$userTagtext", user.getAsTag());
        msg = msg.replace("$$usertagtext", user.getAsTag());

        msg = msg.replace("$$UserTag", user.getAsMention());
        msg = msg.replace("$$Usertag", user.getAsMention());
        msg = msg.replace("$$userTag", user.getAsMention());
        msg = msg.replace("$$usertag", user.getAsMention());

        msg = msg.replace("$$UserName", user.getName());
        msg = msg.replace("$$Username", user.getName());
        msg = msg.replace("$$userName", user.getName());
        msg = msg.replace("$$username", user.getName());

        return msg;
    }

    /**
     * Formats a message by transforming:
     * <br>$$usertag to a mention of the user (@user)
     * <br>$$UserTagText to the name + tag of the user (name#tag)
     * <br>$$username to the name of the user
     * <br>$$role to role mention (or role name)
     * <br>$$game to the game name
     *
     * @param msg         The msg to format.
     * @param role        The role to format. <b>Can be null</b>
     * @param user        The user target of the message.
     * @param useRoleName If the role name should be used isntead of a mention.
     * @return The formatted message.
     */
    public static String formatGameSettingsMessage(String msg, Role role, User user, boolean useRoleName, String gameName) {
        String result = formatSettingsMessage(msg, user, useRoleName);
        result = result.replace("$$game", gameName);
        result = result.replace("$$Game", gameName);

        if (role != null) {
            String replacement;
            if (useRoleName)
                replacement = gameName;
            else
                replacement = role.getAsMention();

            result = result.replace("$$role", replacement);
            result = result.replace("$$Role", replacement);
        }

        return result;
    }

    /**
     * Message all users with a certain role.
     *
     * @param guild            The target guild.
     * @param role             The role.
     * @param message          The message to send.
     * @param exceptionUserIds The users to not message.
     */
    public static void messageAllUsersWithRole(Guild guild, Role role, MessageCreateData message, List<Long> exceptionUserIds) {
        List<Member> membersWithRole = guild.getMembersWithRoles(role);
        if (exceptionUserIds == null)
            exceptionUserIds = new ArrayList<>();

        for (Member member : membersWithRole) {
            long userId = member.getUser().getIdLong();
            if (!exceptionUserIds.contains(userId)) {
                member.getUser().openPrivateChannel().queue(channel -> {
                    channel.sendMessage(message).queue(null, e -> {/* Override println com.github.ArthurSchiavom.old.error message with nothing as this means that the user doesn't allow DMs */});
                });
            }
        }
    }

    /**
     * Verifies if a member has a certain role.
     *
     * @param member The member to verify.
     * @param roleId The ID of the role.
     * @return If the member has the role.
     */
    public static boolean memberHasRole(Member member, long roleId) {
        for (Role role : member.getRoles()) {
            if (role.getIdLong() == roleId)
                return true;
        }
        return false;
    }

    /**
     * Verifies if a member has a certain role.
     *
     * @param member The member to verify.
     * @param roleId The ID of the role.
     * @return If the member has the role.
     */
    public static boolean memberHasRole(Member member, String roleId) {
        long idLong = Long.parseLong(roleId);
        return memberHasRole(member, idLong);
    }

    /**
     * Get the first mentioned channel in a message.
     *
     * @param msg The message to analyze.
     * @return (1) The first channel mentioned if any or (2) null if no channel mentioned.
     */
    public static MessageChannel getSingleMentionedChannel(Message msg) {
        List<TextChannel> mentionedChannels = msg.getMentions().getChannels(TextChannel.class);
        if (mentionedChannels.size() < 1)
            return null;
        else
            return mentionedChannels.get(0);
    }

    /**
     * Get the first mentioned role in an event.
     *
     * @param event The event to analyze.
     * @return (1) The first role mentioned if any or (2) null if no role mentioned.
     */
    public static Role getRoleMentioned(MessageReceivedEvent event) {
        List<Role> rolesMentioned = event.getMessage().getMentions().getRoles();
        if (rolesMentioned.size() < 1) {
            return null;
        } else
            return rolesMentioned.get(0);
    }

    public static final String EMPTY_LIST_TEXT_CONVERSION_MESSAGE = "none";

    /**
     * Calculates a visual representation of a list of strings: String1, String2, String3,...
     *
     * @return A visual representation of the list of strings if any or
     * <br>EMPTY_LIST_TEXT_CONVERSION_MESSAGE constant of this class if the list or null or empty.
     */
    public static String getListdDisplayCommaSeparated(List<String> strings) {
        String result = EMPTY_LIST_TEXT_CONVERSION_MESSAGE;
        if (strings != null && !strings.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> stringsIt = strings.iterator();
            sb.append(stringsIt.next());
            while (stringsIt.hasNext()) {
                String string = stringsIt.next();
                if (stringsIt.hasNext())
                    sb.append(", ");
                else
                    sb.append(" and ");
                sb.append(string);
            }
            result = sb.toString();
        }
        return result;
    }

    /**
     * Creates a displayable list where each element is in a different line and numbered according to the list order.
     *
     * @param separator The separator between the number and the element. These are the only characters between the number and the element.
     * @param elems     The list of elements to display.
     * @return The displayable list calculated.
     */
    public static StringBuilder getListDisplayNumbered(String separator, Object[] elems) {
        StringBuilder sb = new StringBuilder();
        if (elems.length != 0)
            sb.append("**").append(1).append("**").append(separator).append(elems[0]);
        for (int i = 1; i < elems.length; i++) {
            sb.append("\n**").append(i + 1).append("**").append(separator).append(elems[i]);
        }
        return sb;
    }

    /**
     * Calculates a visual representation of a list of strings: String1, String2, String3,...
     *
     * @return A visual representation of the list of strings if any or
     * <br>EMPTY_LIST_TEXT_CONVERSION_MESSAGE constant of this class if the list or null or empty.
     */
    public static String getBoldListTextComma(List<String> strings) {
        String result = EMPTY_LIST_TEXT_CONVERSION_MESSAGE;
        if (strings != null && !strings.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator<String> stringsIt = strings.iterator();
            sb.append("**").append(stringsIt.next()).append("**");
            while (stringsIt.hasNext()) {
                String string = stringsIt.next();
                if (stringsIt.hasNext())
                    sb.append(", ");
                else
                    sb.append(" and ");
                sb.append("**").append(string).append("**");
            }
            result = sb.toString();
        }
        return result;
    }

    public static List<Long> getMemberRolesIds(Member member) {
        List<Long> rolesIds = new ArrayList<>();
        for (Role role : member.getRoles()) {
            rolesIds.add(role.getIdLong());
        }
        return rolesIds;
    }

    /**
     * @param event
     * @param rolesIds
     * @return A user-readable message of the result of the operation.
     */
    public static String batchToggleRoles(Guild guild, Member member, List<Long> rolesIds) {
        String resultMessage = null;
        List<Long> memberRolesIds = Utils.getMemberRolesIds(member);
        List<String> rolesAdded = new ArrayList<>();
        List<String> rolesRemoved = new ArrayList<>();

        try {
            for (long mentionedRoleId : rolesIds) {
                Role role = guild.getRoleById(mentionedRoleId);
                String roleName = role.getName();
                if (memberRolesIds.contains(mentionedRoleId)) {
                    guild.removeRoleFromMember(member, role).complete();
                    rolesRemoved.add(roleName);
                } else {
                    guild.addRoleToMember(member, role).complete();
                    rolesAdded.add(roleName);
                }
            }
        } catch (InsufficientPermissionException e) {
            resultMessage = "No permission to add/remove a role.";
        } catch (HierarchyException e) {
            resultMessage = "No permission to add/remove these roles due to role hierarchy.";
        } catch (Exception e) {
            resultMessage = "Failed to add roles due to unknown com.github.ArthurSchiavom.old.error.";
            e.printStackTrace();
        }

        if (resultMessage == null)
            resultMessage = batchToggleRolesCompileFinalMessage(rolesAdded, rolesRemoved);
        return resultMessage;
    }

    private static String batchToggleRolesCompileFinalMessage(List<String> rolesAddedNames, List<String> rolesRemovedNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("Role");
        boolean hasRoleAdded = !rolesAddedNames.isEmpty();
        if (hasRoleAdded) {
            String pluralOrNot = (rolesAddedNames.size() > 1) ? "s " : " ";
            sb.append(pluralOrNot);
            sb.append(Utils.getBoldListTextComma(rolesAddedNames));
            String wasWere = (rolesAddedNames.size() == 1) ? " was" : " were";
            sb.append(wasWere).append(" added");
        }

        boolean hasRolesRemoved = !rolesRemovedNames.isEmpty();
        if (hasRolesRemoved) {
            if (hasRoleAdded)
                sb.append(" and role");
            String pluralOrNot = (rolesRemovedNames.size() > 1) ? "s " : " ";
            sb.append(pluralOrNot);

            sb.append(Utils.getBoldListTextComma(rolesRemovedNames));
            String wasWere = (rolesRemovedNames.size() == 1) ? " was" : " were";
            sb.append(wasWere).append(" removed");
        }
        sb.append(".");
        return sb.toString();
    }

    /**
     * Converts a calendar com.github.ArthurSchiavom.old.information to full day, month and year format.
     * <br>Check <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#dt">Formatter docs</a> for more info.
     *
     * @param cal The target calendar.
     * @return A full textual representation of the day, month and year.
     */
    public static String calendarToDateDisplay(Calendar cal) {
        return String.format(Locale.ENGLISH
                , "%te of %tB, %tY"
                , cal
                , cal
                , cal);
    }

    /**
     * Converts a calendar com.github.ArthurSchiavom.old.information to 24H time format (HH:MMM).
     * <br>Check <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#dt">Formatter docs</a> for more info.
     *
     * @param cal The target calendar.
     * @return A textual representation of the time as HH:MM.
     */
    public static String calendarToTimeDisplay(Calendar cal) {
        return String.format("%tR"
                , cal);
    }

    /**
     * Converts a calendar com.github.ArthurSchiavom.old.information to full day, month and year format + 24H time format as HH:MM.
     * <br>Check <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#dt">Formatter docs</a> for more info.
     *
     * @param cal The target calendar.
     * @return A full textual representation of the day, month and year + 24H time as HH:MM.
     */
    public static String calendarToDateTimeDisplay(Calendar cal) {
        return calendarToDateDisplay(cal) + " - " + calendarToTimeDisplay(cal);
    }

    public static String millisecondsToMinuteSecondDisplay(long milis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milis) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String millisecondsToYearDaysHoursMinutesDisplay(long milliseconds) {
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
                - TimeUnit.DAYS.toHours(days);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
                - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days);
        long years = (long) (days / 365.242199);
        days %= 365.242199;
        long months = days / 30;
        days %= 30;

        StringBuilder sb = new StringBuilder();
        int nAdded = 0;
        int max = 3;
        millisecondsToYearDaysHoursMinutesDisplayAppend(sb, "Year", years, nAdded, max, false);
        millisecondsToYearDaysHoursMinutesDisplayAppend(sb, "Month", months, nAdded, max, false);
        millisecondsToYearDaysHoursMinutesDisplayAppend(sb, "Day", days, nAdded, max, false);
        millisecondsToYearDaysHoursMinutesDisplayAppend(sb, "Hour", hours, nAdded, max, false);
        millisecondsToYearDaysHoursMinutesDisplayAppend(sb, "Minute", minutes, nAdded, max, true);


        return sb.toString();
    }

    // Todo - give this a better name and maybe rework
    /**
     *
     * @param sb
     * @param valueName
     * @param value
     * @return new number of appended values
     */
    private static int millisecondsToYearDaysHoursMinutesDisplayAppend(StringBuilder sb, String valueName, long value, int nAdded, int max, boolean isLast) {
        boolean shouldAppend = nAdded < max && (value != 0 || isLast);

        if (shouldAppend) {
            if (nAdded > 0) {
                if (isLast)
                    sb.append(" and ");
                else
                    sb.append(", ");
            }

            sb.append(value).append(" ").append(valueName);
            if (value > 1)
                sb.append("s");

            return nAdded + 1;
        }

        return nAdded;
    }

    /**
     * Adjust an embed with only inline fields to have X columns (so it displays properly on PC)
     *
     * @param eb      The embed builder with only inline fields.
     * @param nFields The number of columns it should have.
     */
    public static void adjustEmbedInlineFields(EmbedBuilder eb, int nFields) {
        int nEmbedFields = eb.getFields().size();
        while (nEmbedFields % nFields != 0) {
            eb.addField("", "", true);
            nEmbedFields++;
        }
    }

    public static String getInvisibleCharacter() {
        return "\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00\uDB40\uDC00";
    }

   
    /**
     * Attempts to retrieve a message if it is available.
     *
     * @param jda The JDA instance to use.
     * @param guildId The ID of the guild where the message is.
     * @param channelId The ID of the channel where the message is.
     * @param msgId The ID of the message.
     * @return The message retrieved.
     * @throws MessageUnavailableException If the message wasn't available for the bot account to access.
     * @throws JDANotConnectedException If JDA is not connected to Discord.
     */
    public static Message retrieveMessageIfAvailable(JDA jda, String guildId, String channelId, String msgId) throws MessageUnavailableException, JDANotConnectedException {
        try {
            return jda.getGuildById(guildId).getTextChannelById(channelId).retrieveMessageById(msgId).complete();
        } catch (Exception e) {
            if (e instanceof ErrorResponseException) {
                ErrorResponse errorResponse = ((ErrorResponseException) e).getErrorResponse();
                switch (errorResponse) {
                    case MISSING_ACCESS:
                    case MISSING_PERMISSIONS:
                    case UNKNOWN_MESSAGE:
                    case UNKNOWN_CHANNEL:
                        throw new MessageUnavailableException("The message isn't available. (No permission to access or deleted)");
                }
            }
            throw new JDANotConnectedException("The bot isn't connected to Discord.");
        }
    }

    public static String calcDurationDisplayEmoji(Duration duration) {
        long days = duration.toDays();

        Duration intervalMinusDays = duration.minusDays(days);
        long hours = intervalMinusDays.toHours();

        Duration intervalMinusDaysHours = intervalMinusDays.minusHours(hours);
        long minutes = intervalMinusDaysHours.toMinutes();

        StringBuilder sb = new StringBuilder();
        boolean useDays = days > 0;
        boolean useHours = useDays || hours > 0;

        if (useDays)
            sb.append(convertNumberToEmojiDisplay(days)).append(" Days\n\n");
        if (useHours)
            sb.append(replaceNumbersWithEmojis(String.format("%02d", hours))).append(" Hours\n\n");
        sb.append(replaceNumbersWithEmojis(String.format("%02d", minutes))).append(" Minutes");

        return sb.toString();
    }

    public static String convertNumberToEmojiDisplay(long number) {
        String numberString = Long.toString(number);
        return replaceNumbersWithEmojis(numberString);
    }

    public static String replaceNumbersWithEmojis(String str) {
        return str
                .replace("-", ":heavy_minus_sign:")
                .replace("0", ":zero:")
                .replace("1", ":one:")
                .replace("2", ":two:")
                .replace("3", ":three:")
                .replace("4", ":four:")
                .replace("5", ":five:")
                .replace("6", ":six:")
                .replace("7", ":seven:")
                .replace("8", ":eight:")
                .replace("9", ":nine:");
    }


    /**
     * Gets the updateNextExecutionTime week day of the list given.
     *
     * @param daysOfWeek The days of the week according to the class Calendar.
     * @return The closest updateNextExecutionTime day of the week that matches one of the list.
     */
    public static Calendar getNextDayOfWeek(List<Integer> daysOfWeek, boolean includeToday, int hour, int minute) {
        Calendar timeNow = Calendar.getInstance();
        Calendar timeResult = Calendar.getInstance();
        timeResult.set(Calendar.HOUR_OF_DAY, hour);
        timeResult.set(Calendar.MINUTE, minute);
        timeResult.set(Calendar.SECOND, 0);
        timeResult.set(Calendar.MILLISECOND, 0);

        int dayToday = timeNow.get(Calendar.DAY_OF_WEEK);
        if (includeToday && daysOfWeek.contains(dayToday) && timeResult.after(timeNow)) {
            return timeResult;
        }

        do {
            timeResult.add(Calendar.DAY_OF_MONTH, 1);
        } while (!daysOfWeek.contains(timeResult.get(Calendar.DAY_OF_WEEK)));
        return timeResult;
    }


    /**
     * Gets the updateNextExecutionTime day of the month (excluding today) that is present in the list.
     * <br><b>Days bigger than the biggest day of a month are converted to the biggest day of the month.</b>
     *
     * @param targetDays The target days.
     * @param hour       The hour to set in the result.
     * @param minute     The minute to set in the result.
     * @return The resulting Calendar.
     */
    public static Calendar getNextDayOfMonth(List<Integer> targetDays, boolean includeToday, int hour, int minute) {
        if (targetDays.isEmpty())
            throw new IllegalArgumentException("The list of days is empty.");

        Calendar timeNow = Calendar.getInstance();
        Calendar timeResult = Calendar.getInstance();
        timeResult.set(Calendar.HOUR_OF_DAY, hour);
        timeResult.set(Calendar.MINUTE, minute);
        timeResult.set(Calendar.SECOND, 0);
        timeResult.set(Calendar.MILLISECOND, 0);

        int dayToday = timeNow.get(Calendar.DAY_OF_MONTH);
        if (includeToday && targetDays.contains(dayToday) && timeResult.after(timeNow))
            return timeResult;

        int dayChosenThisMonth = -1;
        int biggestDayThisMonth = timeResult.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Integer> targetDaysThisMonth = limitBiggestNumberInList(targetDays, biggestDayThisMonth);
        Collections.sort(targetDaysThisMonth);
        int currentDay = timeResult.get(Calendar.DAY_OF_MONTH);
        for (Integer targetDay : targetDaysThisMonth) {
            if (targetDay > currentDay && targetDay <= biggestDayThisMonth) {
                dayChosenThisMonth = targetDay;
                break;
            }
        }

        if (dayChosenThisMonth != -1) {
            timeResult.set(Calendar.DAY_OF_MONTH, dayChosenThisMonth);
        } else {
            timeResult.add(Calendar.MONTH, 1);
            int biggestDayNextMonth = timeResult.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<Integer> targetDaysNextMonth = limitBiggestNumberInList(targetDays, biggestDayNextMonth);
            Collections.sort(targetDaysNextMonth);
            timeResult.set(Calendar.DAY_OF_MONTH, targetDaysNextMonth.get(0));
        }
        return timeResult;
    }

    /**
     * Calculates a list with all the elements bigger than X replaced by X.
     *
     * @param list          The target list.
     * @param biggestNumber X.
     * @return The calculated list.
     */
    public static List<Integer> limitBiggestNumberInList(List<Integer> list, int biggestNumber) {
        List<Integer> result = new ArrayList<>(list);
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) > biggestNumber) {
                result.remove(i);
                result.add(i, biggestNumber);
            }
        }
        return result;
    }

    /**
     * Finds the days of the week mentioned in a message.
     *
     * @param msg The message to anayze.
     * @return The days contained in the message according to the constants in Calendar.
     */
   
    public static List<Integer> getMentionedDaysOfWeek(String msg) {
        List<Integer> mentionedDaysOfWeek = new ArrayList<>();
        msg = msg.toLowerCase();
        if (msg.contains("mon"))
            mentionedDaysOfWeek.add(Calendar.MONDAY);
        if (msg.contains("tue"))
            mentionedDaysOfWeek.add(Calendar.TUESDAY);
        if (msg.contains("wed"))
            mentionedDaysOfWeek.add(Calendar.WEDNESDAY);
        if (msg.contains("thu"))
            mentionedDaysOfWeek.add(Calendar.THURSDAY);
        if (msg.contains("fri"))
            mentionedDaysOfWeek.add(Calendar.FRIDAY);
        if (msg.contains("sat"))
            mentionedDaysOfWeek.add(Calendar.SATURDAY);
        if (msg.contains("sun"))
            mentionedDaysOfWeek.add(Calendar.SUNDAY);
        return mentionedDaysOfWeek;
    }

   
    public static List<Integer> extractNumbersSeparatedByWhitespace(String msg) {
        String[] valuesString = msg.split(" ");
        List<Integer> valuesInt = new ArrayList<>();
        for (String valueString : valuesString) {
            try {
                int valueInt = Integer.parseInt(valueString);
                valuesInt.add(valueInt);
            } catch (Exception e) {
            }
        }
        return valuesInt;
    }

    /**
     * Converts a string that represents the time (HH:MM) in an array where the first value is the hour and the second is the minute.
     *
     * @param timeString The string that represents the time (HH:MM)
     * @return (1) An array where the first value is the hour and the second is the minute if the string is a valid time or
     * <br>(2) null if the string doesn't represent a valid time.
     */
    public static int[] TimeStringToIntArray(String timeString) {
        String[] hourMinuteString = timeString.split(":");
        if (hourMinuteString.length != 2)
            return null;

        int hour, minute;
        try {
            hour = Integer.parseInt(hourMinuteString[0]);
            minute = Integer.parseInt(hourMinuteString[1]);
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
                return null;
        } catch (Exception e) {
            return null;
        }

        int[] result = {hour, minute};
        return result;
    }

    public static List<String> integerListToStringList(List<Integer> integers) {
        List<String> result = new ArrayList<>();
        for (Integer integer : integers) {
            result.add(integer.toString());
        }
        return result;
    }

    public static List<Member> getAllMembersOfStatus(boolean allowRepeatedUsers, boolean ignoreBots, OnlineStatus... validOnlineStatuses) {
        List<Guild> guilds = Bot.getJdaInstance().getGuilds();
        List<Member> result = new ArrayList<>();

        for (Guild guild : guilds) {
            List<Member> membersToAdd = filterMembersByStatus(guild.getMembers(), ignoreBots, validOnlineStatuses);

            for (Member memberToAdd : membersToAdd) {
                boolean canAdd = true;

                if (!allowRepeatedUsers) {
                    for (Member member : result) {
                        if (memberToAdd.getIdLong() == member.getIdLong()) {
                            canAdd = false;
                            break;
                        }
                    }
                }

                if (canAdd)
                    result.add(memberToAdd);
            }
        }

        return result;
    }

    /**
     * Finds all members that are not offline from a list.
     *
     * @param members The list of members.
     * @return all members from the list that are not offline.
     */
    public static List<Member> filterMembersByStatus(List<Member> members, boolean ignoreBots, OnlineStatus... validOnlineStatuses) {
        List<Member> filteredMembers = new ArrayList<>();
        List<OnlineStatus> validOnlineStatusesList = Arrays.asList(validOnlineStatuses);
        for (Member member : members) {
            boolean canAdd = true;

            if (ignoreBots && member.getUser().isBot())
                canAdd = false;

            if (canAdd && !validOnlineStatusesList.contains(member.getOnlineStatus()))
                canAdd = false;

            if (canAdd)
                filteredMembers.add(member);
        }
        return filteredMembers;
    }

    public static String extractPage(String text, String whereToSplit, int pageNumber, int maxCharactersPerPage) {
        if (pageNumber < 1)
            throw new IllegalArgumentException("Page number must be bigger than 0.");

        if (text.isEmpty())
            return "";

        String currentPage;
        int currentPageNumber = 0;
        int lowerLimitExclusive = 0;
        int higherLimitInclusive = 0;
        int previousLowerLimitExclusive = 0;
        while (currentPageNumber != pageNumber) {
            currentPageNumber++;
            previousLowerLimitExclusive = lowerLimitExclusive;
            lowerLimitExclusive = higherLimitInclusive;
            higherLimitInclusive += maxCharactersPerPage;

            if (lowerLimitExclusive >= text.length())
                return text.substring(previousLowerLimitExclusive, lowerLimitExclusive);
            else if (higherLimitInclusive > text.length())
                currentPage = text.substring(lowerLimitExclusive, text.length());
            else
                currentPage = text.substring(lowerLimitExclusive, higherLimitInclusive);

            int higherLimitCurrentPage = currentPage.lastIndexOf(whereToSplit) + 1;
            if (higherLimitCurrentPage == 0)
                higherLimitCurrentPage = currentPage.length();
            higherLimitInclusive = lowerLimitExclusive + higherLimitCurrentPage;

            if (currentPageNumber == pageNumber) {
                return text.substring(lowerLimitExclusive, higherLimitInclusive);
            }
        }
        return "ERROR";
    }
}
