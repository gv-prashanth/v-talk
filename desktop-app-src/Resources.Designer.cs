using Microsoft.VisualBasic;
using Microsoft.VisualBasic.CompilerServices;
using System;
using System.CodeDom.Compiler;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;
using System.Globalization;
using System.Resources;
using System.Runtime.CompilerServices;

namespace vtalk.My.Resources
{
	[CompilerGenerated]
	[DebuggerNonUserCode]
	[GeneratedCode("System.Resources.Tools.StronglyTypedResourceBuilder", "2.0.0.0")]
	[HideModuleName]
	internal static class Resources
	{
		private static System.Resources.ResourceManager resourceMan;

		private static CultureInfo resourceCulture;

		internal static Bitmap bg
		{
			get
			{
				object objectValue = RuntimeHelpers.GetObjectValue(vtalk.My.Resources.Resources.ResourceManager.GetObject("bg", vtalk.My.Resources.Resources.resourceCulture));
				return (Bitmap)objectValue;
			}
		}

		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static CultureInfo Culture
		{
			get
			{
				return vtalk.My.Resources.Resources.resourceCulture;
			}
			set
			{
				vtalk.My.Resources.Resources.resourceCulture = value;
			}
		}

		[EditorBrowsable(EditorBrowsableState.Advanced)]
		internal static System.Resources.ResourceManager ResourceManager
		{
			get
			{
				if (object.ReferenceEquals(vtalk.My.Resources.Resources.resourceMan, null))
				{
					vtalk.My.Resources.Resources.resourceMan = new System.Resources.ResourceManager("vtalk.Resources", typeof(vtalk.My.Resources.Resources).Assembly);
				}
				return vtalk.My.Resources.Resources.resourceMan;
			}
		}
	}
}