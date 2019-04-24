//
//  SignInViewController.swift
//  IOS Release
//
//  Created by Rudy on 4/23/19.
//  Copyright © 2019 Tru Nguyen. All rights reserved.
//

import UIKit
import GoogleSignIn

class SignInViewController: UIViewController, GIDSignInUIDelegate {
    
    @IBOutlet weak var signInButton: GIDSignInButton!
    @IBOutlet weak var startButton: UIButton!
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        GIDSignIn.sharedInstance().uiDelegate = self
        // Uncomment to automatically sign in the user.
        //GIDSignIn.sharedInstance().signInSilently()
        
        if delegate.signedIn == true {
            signInButton.isHidden = true
        } else {
            signInButton.isHidden = false
        }
    }
}
