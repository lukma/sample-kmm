//
//  GPlayApp.swift
//  Shared
//
//  Created by Lukma Gayo Arizky on 17/08/22.
//

import SwiftUI

@main
struct GPlayApp: App {
    @UIApplicationDelegateAdaptor private var appDelegate: AppDelegate

    var body: some Scene {
        WindowGroup {
            AppCoordinator()
        }
    }
}
