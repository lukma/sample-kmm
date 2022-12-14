//
//  LoginView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 21/08/22.
//

import SwiftUI
import common

struct LoginView: View {
    private var onSignedIn: (() -> Void)? = nil
    @StateObject private var uiState = LoginUiState()
    
    init(onSignedIn: @escaping () -> Void) {
        self.onSignedIn = onSignedIn
    }
    
    var body: some View {
        ZStack {
            VStack(alignment: .trailing, spacing: 16) {
                VStack(alignment: .leading) {
                    HStack {
                        Image(systemName: "person.fill").foregroundColor(.gray)
                        TextField("Username", text: $uiState.username)
                            .onChange(of: uiState.username, perform: { value in
                                validate(.username, value)
                            })
                            #if os(iOS)
                            .autocapitalization(.none)
                            #endif
                    }
                    .padding()
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(.gray, lineWidth: 1))
                    
                    Text(uiState.validations.errorMessage(forKey: .username))
                        .foregroundColor(.red)
                }
                
                
                VStack(alignment: .leading) {
                    HStack {
                        Image(systemName: "lock.fill").foregroundColor(.gray)
                        SecureField("Password", text: $uiState.password)
                            .onChange(of: uiState.password, perform: { value in
                                validate(.password, value)
                            })
                    }
                    .padding()
                    .overlay(RoundedRectangle(cornerRadius: 5).stroke(.gray, lineWidth: 1))
                    
                    Text(uiState.validations.errorMessage(forKey: .password))
                        .foregroundColor(.red)
                }

                Button("Sign In", action: signIn)
                    .buttonStyle(.borderedProminent)
                    .disabled(uiState.isSignedInButtonDisabled)
                    .alert(uiState.errorMessage, isPresented: $uiState.isError) {
                        Button("Retry", action: signIn)
                        Button("Dismiss", action: {
                            uiState.isError = false
                            uiState.errorMessage = ""
                        })
                    }
            }
            .padding()
            
            Color.black
                .opacity(uiState.isLoading ? 0.3 : 0)
                .edgesIgnoringSafeArea(.all)
            
            ProgressView()
                .opacity(uiState.isLoading ? 1 : 0)
        }
    }
}

extension LoginView {
    private func validate(_ field: LoginFormSpec, _ value: String) {
        Task {
            let toValidate = FieldToValidate(
                key: field.rawValue,
                rules: field.rules(),
                value: value
            )
            let validations = [String:ValidationState](
                uniqueKeysWithValues: uiState.validations.map { (key, value) in
                    (key.rawValue, value)
                }
            )
            let param = FormValidationUseCase.Param(
                toValidate: toValidate,
                current: validations
            )
            let result = await CommonDependencies.shared.formValidationUseCase.perform(param)
            if case let .success(validations) = result {
                uiState.validations = [LoginFormSpec:ValidationState](
                    uniqueKeysWithValues: validations.compactMap { (key, value) in
                        guard let validationKey = LoginFormSpec(rawValue: key) else { return nil }
                        return (validationKey, value)
                    }
                )
            }
        }
    }
    
    private func signIn() {
        Task {
            uiState.isLoading = true
            uiState.isError = false
            uiState.errorMessage = ""
            
            let param = SignInUseCase.Param(
                username: uiState.username,
                password: uiState.password
            )
            let result = await CommonDependencies.shared.signedInUseCase.perform(param)
            switch result {
            case .success(_):
                onSignedIn?()
            case .failure(let error):
                uiState.isLoading = false
                uiState.isError = true
                uiState.errorMessage = (error as NSError).domain
            }
        }
    }
}

class LoginUiState: ObservableObject {
    @Published var username = ""
    @Published var password = ""
    @Published var isLoading = false
    @Published var isError = false
    @Published var errorMessage = ""
    @Published var validations: [LoginFormSpec:ValidationState] = [
        .username: ValidationState.None(),
        .password: ValidationState.None()
    ]

    var isSignedInButtonDisabled: Bool {
        get {
            validations.contains { (_, state) in
                state is ValidationState.None || state is ValidationState.Invalid
            }
        }
    }
}

enum LoginFormSpec: String {
    case username = "username"
    case password = "password"
    
    func rules() -> [FieldRule] {
        switch self {
        case .username:
            return [FieldRule.NoFieldBlank()]
        case .password:
            return [FieldRule.NoFieldBlank()]
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(onSignedIn: {})
    }
}
