// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.xrp.XRPServo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  // STEP 1: We need a XRPServo for the arm
  private final XRPServo m_armServo;

  /** Creates a new Arm. */
  public Arm() {
    // STEP 2: Assign the servo to channel 4 (corresponds to Servo1 on the board)
    m_armServo = new XRPServo(4);
  }

  // STEP 3: Provide a means to set the arm angle
  public void setAngle(double angleDeg) {
    m_armServo.setAngle(angleDeg);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
