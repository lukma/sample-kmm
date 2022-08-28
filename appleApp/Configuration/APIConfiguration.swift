//
//  APIConfiguration.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 18/08/22.
//

class APIConfiguration {
    static let shared = APIConfiguration()

    var apiHost: String {
        switch BuildConfiguration.shared.environment {
        case .debugMock, .releaseMock:
            return "virtserver.swaggerhub.com"
        case .debugDevelopment, .releaseDevelopment:
            return "api.dev.gplay.com"
        }
    }
    
    var apiPrefixPath: String {
        switch BuildConfiguration.shared.environment {
        case .debugMock, .releaseMock:
            return "/lukma/gplay/"
        case .debugDevelopment, .releaseDevelopment:
            return ""
        }
    }
}
