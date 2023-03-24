package com.example.plugin.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Host {
   
	public String host;
	public String name;
	public String connection_state;
	public String power_state;
   
 }
