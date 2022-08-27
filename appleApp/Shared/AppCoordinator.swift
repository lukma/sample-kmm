//
//  AppCoordinator.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 23/08/22.
//

import SwiftUI
import FlowStacks
import common

struct AppCoordinator: View {
    @State private var routes: Routes<Screen> = [.root(.none)]
    
    var body: some View {
        Router($routes) { screen, _ in
            switch screen {
            case .none:
                Text("Loading…")
                    .task { await initRootScreen() }
            case .login:
                LoginView(onSignedIn: onSignedIn)
            case .home:
                HomeView()
            }
        }
    }
}

extension AppCoordinator {
    private func initRootScreen() async {
        let result = await CommonDependencies.shared.isSignedInUseCase.perform()
        switch result {
        case .success(let isSignedIn):
            routes = [.root(isSignedIn ? .home : .login)]
        case .failure(_):
            routes = [.root(.login)]
        }
    }
    
    private func onSignedIn() {
        routes = [.root(.home)]
    }
}

enum Screen {
    case none
    case login
    case home
}

struct AppCoordinator_Previews: PreviewProvider {
    static var previews: some View {
        AppCoordinator()
    }
}
