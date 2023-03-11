package team.microchad.service.scheduler



enum class SchedulerSlot(val cronExpression: String) {
    MONDAY("0 0 6 ? * 2"),
    FRIDAY("0 0 15 ? * 6"),
    DAILY("0 1 9 ? * 2-6")
}

fun configureScheduler() {

    scheduleMessageSending("weeklyNew", SchedulerSlot.MONDAY, "=\"NEW\"")
    scheduleMessageSending("weeklyRts", SchedulerSlot.MONDAY, "=\"READY TO SPECIFICATION\"")
    scheduleMessageSending("weeklyRtd", SchedulerSlot.MONDAY, "=\"READY TO DEVELOP\"")

    scheduleMessageSending("weeklyReview", SchedulerSlot.FRIDAY, "changed to \"REVIEW\"")
    scheduleMessageSending("weeklyReady", SchedulerSlot.FRIDAY, "=\"READY\"")
    scheduleMessageSending("weeklyReleased", SchedulerSlot.FRIDAY, "=\"RELEASED\"")

    scheduleMessageSending("dailyRtr", SchedulerSlot.DAILY, "=\"READY TO REVIEW\"")
    scheduleMessageSending("dailyDevelopDone", SchedulerSlot.DAILY, "=\"DEVELOP DONE\"")
}