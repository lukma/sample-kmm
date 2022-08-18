//
//  BuildConfiguration.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 18/08/22.
//

import Foundation

enum Environment: String {
    case debugMock = "Debug Mock"
    case releaseMock = "Release Mock"
    
    case debugDevelopment = "Debug Development"
    case releaseDevelopment = "Release Development"
}

class BuildConfiguration {
    static let shared = BuildConfiguration()
    
    var environment: Environment
    
    init() {
        let currentConfiguration = Bundle.main.object(forInfoDictionaryKey: "Configuration") as! String
        environment = Environment(rawValue: currentConfiguration)!
    }
}
