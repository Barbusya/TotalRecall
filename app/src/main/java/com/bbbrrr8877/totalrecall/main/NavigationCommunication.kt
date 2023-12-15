package com.bbbrrr8877.totalrecall.main

import com.bbbrrr8877.totalrecall.core.Communication

interface NavigationCommunication {

    interface Update : Communication.Update<Screen>

    interface Observe : Communication.Observe<Screen>

    interface Mutable : Observe, Update

    class Base : Communication.Abstract<Screen>(), Mutable
}