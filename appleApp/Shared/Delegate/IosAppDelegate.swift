//
//  IosAppDelegate.swift
//  GPlay (iOS)
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import SwiftUI

class IosAppDelegate: NSObject, UIApplicationDelegate, AppDelegateHelper {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        initKoin()
        return true
    }
}
