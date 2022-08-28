//
//  DelegateHelper.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import common

protocol DelegateHelper { }

extension DelegateHelper {
    func initKoin() {
        let appConfig = AppConfig(
            apiHost: APIConfiguration.shared.apiHost,
            apiPrefixPath: APIConfiguration.shared.apiPrefixPath
        )
        KoinKt.doInitKoin(appConfig: appConfig)
    }
}
