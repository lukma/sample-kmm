//
//  GPlayApp.swift
//  Shared
//
//  Created by Lukma Gayo Arizky on 17/08/22.
//

import SwiftUI

@main
struct GPlayApp: App {
    
    #if os(iOS)
        @UIApplicationDelegateAdaptor private var appDelegate: IosAppDelegate
    #elseif os(OSX)
        @NSApplicationDelegateAdaptor var appDelegate: MacosAppDelegate
    #endif

    var body: some Scene {
        WindowGroup {
            AppCoordinator()
        }
    }
}
