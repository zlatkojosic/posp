import {NativeModules} from 'react-native';

module.exports = NativeModules.Printer;


export const Unknown = -666
export const Success = 0
export const Busy = 1
export const OutOfPaper = 2
export const FormatDataPacketError = 3
export const Malfunctions = 4
export const Overheats = 8
export const VoltageToLow = 9
export const Unfinished = 16
/*
  Next two have the same error code.
  Probably an error in the documentation.
 */
export const FontLibraryNotInstalled = -2
export const DataPackageTooLong = -2
export const PrinterNotFound = -667

