//
//  APIConfiguration.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 18/08/22.
//

class APIConfiguration {
    var apiHost: String {
        switch BuildConfiguration.shared.environment {
        case .debugMock, .releaseMock:
            return "virtserver.swaggerhub.com/lukma/gplay"
        case .debugDevelopment, .releaseDevelopment:
            return "api.gplay.com"
        }
    }
}
