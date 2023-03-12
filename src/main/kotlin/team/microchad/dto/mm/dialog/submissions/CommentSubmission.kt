package team.microchad.dto.mm.dialog.submissions;

import kotlinx.serialization.Serializable;

@Serializable
class CommentSubmission(
    val issue: String,
    val comment: String
) : Submission()
