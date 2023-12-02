// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveForDistance;
import frc.robot.commands.DriveUntilDistanceAway;
import frc.robot.commands.TurnDegrees;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Chassis m_chassis = new Chassis();

  // STEP 1: Instantiate a new arm
  private final Arm m_arm = new Arm();

  // STEP 2: Remove the example command

  // STEP 3: Instantiate the Gamepad/Joystick
  private CommandXboxController m_gamepad = new CommandXboxController(0);

  // A chooser for autonomous commands
  private SendableChooser<Command> m_chooser = new SendableChooser<>();

  // Instant commands for raising and lowering arm
  private Command m_lowerArm = new InstantCommand(() -> m_arm.setAngle(0), m_arm);
  private Command m_raiseArm = new InstantCommand(() -> m_arm.setAngle(30), m_arm);

  private Command m_driveUntilDistance = new DriveUntilDistanceAway(0.5, 6, m_chassis);
  private Command m_turn180 = new TurnDegrees(0.5, 180, m_chassis);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // STEP 4: Configure the default arcade drive command for the drivetrain
    m_chassis.setDefaultCommand(
      Commands.run(
        () -> 
          m_chassis.arcadeDrive(-m_gamepad.getRawAxis(1), -m_gamepad.getRawAxis(2)),
        m_chassis));

    Command autoCommand = Commands.sequence(
      m_lowerArm,
      new DriveForDistance(-0.5, 12, m_chassis),
      m_raiseArm,
      new WaitCommand(0.5),
      m_driveUntilDistance,
      m_turn180
    );

    // To test commands, we can add them to the dashboard
    SmartDashboard.putData("Lower Arm", m_lowerArm);
    SmartDashboard.putData("Raise Arm", m_raiseArm);
    SmartDashboard.putData("Drive Until 6in Away", m_driveUntilDistance);
    SmartDashboard.putData("Auto", autoCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO We will add button configurations here
    m_gamepad.a().onTrue(m_lowerArm);
    m_gamepad.b().onTrue(m_raiseArm);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}
