//
//  AppCoordinator.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 23/08/22.
//

import SwiftUI
import FlowStacks

struct AppCoordinator: View {
    @State var routes: Routes<Screen> = [.root(.home)]
    
    var body: some View {
        Router($routes) { screen, _ in
            switch screen {
            case .login:
                LoginView()
            case .home:
                HomeView()
            }
        }
        .onAppear(perform: checkIsSignedIn)
    }
}

extension AppCoordinator {
    func checkIsSignedIn() {
        // Todo - invoke IsSignedInUseCase
    }
}

enum Screen {
    case login
    case home
}

struct AppCoordinator_Previews: PreviewProvider {
    static var previews: some View {
        AppCoordinator()
    }
}
