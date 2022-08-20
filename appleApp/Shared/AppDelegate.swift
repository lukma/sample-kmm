//
//  GPlayAppDelegate.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 19/08/22.
//

import SwiftUI
import common

class AppDelegate: NSObject, UIApplicationDelegate, ObservableObject {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        // Setup Koin
        let appConfig = AppConfig(
            apiHost: APIConfiguration.shared.apiHost
        )
        KoinKt.doInitKoin(appConfig: appConfig)
        
        return true
    }
}
