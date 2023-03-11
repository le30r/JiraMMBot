package team.microchad.service.scheduler

import team.microchad.service.getIssuesWithChangedStatus
import team.microchad.service.getIssuesWithStatus

const val WEEKLY_CRON_MONDAY_EXPRESSION = "0 0 6 ? * 2"  // 9:00 every Monday
const val WEEKLY_FRIDAY_CRON_EXPRESSION = "0 0 15 ? * 6" // 18:00 every Friday
const val DAILY_CRON_EXPRESSION = "0 1 9 ? * 2-6" // 9:01 every working day
const val DEBUG_CRON_EXPRESSION = "*/10 * * ? * *" // every 30 sec


fun configureScheduler() {

    scheduleMessageSending("weeklyNew", WEEKLY_CRON_MONDAY_EXPRESSION, "=\"NEW\"")
    scheduleMessageSending("weeklyRts", WEEKLY_CRON_MONDAY_EXPRESSION, "=\"READY TO SPECIFICATION\"")
    scheduleMessageSending("weeklyRtd", WEEKLY_CRON_MONDAY_EXPRESSION, "=\"READY TO DEVELOP\"")

    scheduleMessageSending("weeklyReview", WEEKLY_FRIDAY_CRON_EXPRESSION, "changed to \"REVIEW\"")
    scheduleMessageSending("weeklyReady", WEEKLY_FRIDAY_CRON_EXPRESSION, "=\"READY\"")
    scheduleMessageSending("weeklyReleased", WEEKLY_FRIDAY_CRON_EXPRESSION, "=\"RELEASED\"")

    scheduleMessageSending("dailyRtr", DAILY_CRON_EXPRESSION, "=\"READY TO REVIEW\"")
    scheduleMessageSending("dailyDevelopDone", DAILY_CRON_EXPRESSION, "=\"DEVELOP DONE\"")
}