package com.jccword.behaviour.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var recording: Recording? = null
}
