//
//  SignInViewController.swift
//  StackRecette
//
//  Created by Rudy on 4/24/19.
//  Copyright © 2019 Rudy. All rights reserved.
//

import UIKit
import GoogleSignIn

class SignInViewController: UIViewController, GIDSignInUIDelegate {
    
    @IBOutlet weak var signInButton: GIDSignInButton!
    @IBOutlet weak var startButton: UIButton!
    
    @IBOutlet weak var usernameLbl: UILabel!
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        GIDSignIn.sharedInstance().uiDelegate = self
        // Uncomment to automatically sign in the user.
        //GIDSignIn.sharedInstance().signInSilently()
        
        //Hide username if not logged in
        usernameLbl.isHidden = true
        
        //Hide Button if the user is signed in
        if delegate.signedIn == true {
            signInButton.isHidden = true
            usernameLbl.isHidden = false
            usernameLbl.text = "Welcome! \(delegate.user)"
        } else {
            signInButton.isHidden = false
        }
    }
}
