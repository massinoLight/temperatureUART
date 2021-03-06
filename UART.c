/*
 * Project name:
     UART (Simple usage of UART module library functions)
 * Copyright:
     (c) Mikroelektronika, 2011.
 * Revision History:
     20110929:
       - initial release (FJ);
 * Description:
     This code demonstrates how to use uart library routines. Upon receiving
     data via RS232, MCU immediately sends it back to the sender.
 * Test configuration:
     MCU:             PIC18F45K22
                      http://ww1.microchip.com/downloads/en/DeviceDoc/41412D.pdf
     Dev.Board:       EasyPIC7 - ac:UART
                      http://www.mikroe.com/eng/products/view/757/easypic-v7-development-system/
     Oscillator:      HS-PLL 32.0000 MHz, 8.0000 MHz Crystal
     Ext. Modules:    None.
     SW:              mikroC PRO for PIC
                      http://www.mikroe.com/eng/products/view/7/mikroc-pro-for-pic/
 * NOTES:
     - Turn on RX and TX UART switches (SW1.1 and SW2.1). (board specific)
     - Put RX and TX UART jumpers (J3 and J4) in RS-232 or USB position,
       depending on your choice (board specific).
 */

unsigned int temp_res = 0;
float temp;

void main() {
  ANSELC = 0;                     // Configure PORTC pins as digital

  UART1_Init(9600);               // Initialize UART module at 9600 bps
  Delay_ms(100);                  // Wait for UART module to stabilize

  UART1_Write_Text("Start");
  UART1_Write(13);
  UART1_Write(10);



 temp_res = 0;
  do {
    temp_res = ADC_Get_Sample(6);     // Get 10-bit results of AD conversion
    temp = (temp_res * VREF)/10.240;  // Calculate temperature in Celsuis
                                      //  change Vref constant according
                                      //  to the power supply voltage
    UART1_Write(temp);                 // and send data via UART

    Delay_ms(300);
  } while(1);



}










