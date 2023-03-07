package team.microchad.service.scheduler

import team.microchad.service.getIssuesWithChangedStatus
import team.microchad.service.getIssuesWithStatus

const val WEEKLY_CRON_MONDAY_EXPRESSION = "0 0 6 ? * 2"  // 9:00 every Monday
const val WEEKLY_FRIDAY_CRON_EXPRESSION = "0 0 15 ? * 6" // 18:00 every Friday
const val DAYLY_CRON_EXPRESSION = "0 1 9 ? * 2-6" // 9:01 every working day


fun configureScheduler() {
    scheduleMessageSending("weeklyNew", WEEKLY_CRON_MONDAY_EXPRESSION, getIssuesWithStatus("NEW"))
    scheduleMessageSending("weeklyRts", WEEKLY_CRON_MONDAY_EXPRESSION, getIssuesWithStatus("READY TO SPECIFICATION"))
    scheduleMessageSending("weeklyRtd", WEEKLY_CRON_MONDAY_EXPRESSION, getIssuesWithStatus("READY TO DEVELOP"))

    scheduleMessageSending("weeklyReview", WEEKLY_FRIDAY_CRON_EXPRESSION, getIssuesWithChangedStatus("REVIEW"))
    scheduleMessageSending("weeklyReady", WEEKLY_FRIDAY_CRON_EXPRESSION, getIssuesWithStatus("READY"))
    scheduleMessageSending("weeklyReleased", WEEKLY_FRIDAY_CRON_EXPRESSION, getIssuesWithStatus("RELEASED"))

    scheduleMessageSending("dailyRtr", DAYLY_CRON_EXPRESSION, getIssuesWithStatus("READY TO REVIEW"))
    scheduleMessageSending("dailyDevelopDone", DAYLY_CRON_EXPRESSION, getIssuesWithStatus("DEVELOP DONE"))
}