/* Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

qx.Class.define("android_widget_CheckBox", {
  extend: android_view_View, // TODO not the correct base class
  construct: function() {
    this.base(arguments);
    this.qx = new qx.ui.form.CheckBox();
  },
  members: {
    $$init____android_content_Context: function(context) {
    },
    $setChecked___boolean: function(flag) {
    	this.qx.setValue(flag == 1);
    },
    $isChecked: function() {
    	return this.qx.getValue();
    }
  }
});