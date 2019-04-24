//
//  ViewController.swift
//  IOS Release
//
//  Created by Tru Nguyen on 4/23/19.
//  Copyright © 2019 Tru Nguyen. All rights reserved.
//

import UIKit
import GoogleSignIn

class ViewController: UIViewController, GIDSignInUIDelegate {
    
    @IBOutlet weak var signOutButton: UIButton!
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if delegate.signedIn == false {
            signOutButton.isHidden = true
        } else {
            signOutButton.isHidden = false
        }
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    //Handle signout
    @IBAction func didTapSignOut(_ sender: AnyObject) {
        GIDSignIn.sharedInstance()?.signOut()
        delegate.signedIn = false
    }
    
    @IBAction func unwindToMain(_ sender: UIStoryboardSegue) {
    }
}
