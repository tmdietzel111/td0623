package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for the Tool
 * <p>
 * Design note - toolCode is a String, since that feels like we will be adding from an external source
 * ToolType is more static, so left it as an enum
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
	private String toolCode;
	private ToolType toolType;
	private String brand;

}
